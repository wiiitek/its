package pl.kubiczak.test.spring.integration.demo.server.config

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator
import io.opentelemetry.api.metrics.MeterProvider
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.context.propagation.TextMapPropagator
import io.opentelemetry.exporter.logging.LoggingMetricExporter
import io.opentelemetry.exporter.logging.LoggingSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.metrics.SdkMeterProvider
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenTelemetryConfig {

    // https://opentelemetry.io/docs/instrumentation/java/manual/#manual-instrumentation-setup
    @Bean
    fun openTelemetry(): OpenTelemetry {
        val resource: Resource = Resource.getDefault().toBuilder()
            .put("service.name", "its-server")
            .put("service.version", "0.0.1-SNAPSHOT").build()

        val metricExporter = LoggingMetricExporter.create()
        val periodicMetricReader = PeriodicMetricReader.builder(metricExporter).build()

        val sdkMeterProvider = SdkMeterProvider.builder()
            .registerMetricReader(periodicMetricReader)
            .setResource(resource)
            .build()

        val sdkTracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
            .setResource(resource)
            .build()

        return OpenTelemetrySdk.builder()
            .setMeterProvider(sdkMeterProvider)
            .setTracerProvider(sdkTracerProvider)
            // propagators
            .setPropagators(
                ContextPropagators.create(
                    TextMapPropagator.composite(
                        W3CTraceContextPropagator.getInstance(),
                        W3CBaggagePropagator.getInstance()
                    )
                )
            )
            .buildAndRegisterGlobal();
    }

    @Bean
    fun tracer(openTelemetry: OpenTelemetry): Tracer =
        openTelemetry.getTracer(
            "tracer-scope-name"
        )

    @Bean
    fun sdkMeterProvider(openTelemetry: OpenTelemetry): MeterProvider =
        openTelemetry.meterProvider
}
