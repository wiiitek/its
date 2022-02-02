package pl.kubiczak.test.spring.integration.demo.cats

import pl.kubiczak.test.spring.integration.demo.cats.CatFactClient.*

class CatFactClientSimple : CatFactClient {

    override fun next(): CatFactDto = CatFactDto("", 0)
}
