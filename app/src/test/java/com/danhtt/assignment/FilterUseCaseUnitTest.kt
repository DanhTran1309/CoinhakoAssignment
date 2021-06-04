package com.danhtt.assignment

import com.danhtt.assignment.domain.FilterUseCaseImpl
import com.danhtt.assignment.domain.model.Currency
import org.junit.Assert.*
import org.junit.Test

class FilterUseCaseUnitTest {
    private val filterUseCase = FilterUseCaseImpl()
    private val originalList = listOf(
        Currency(
            "LTC",
            "USD",
            "179.83",
            "179.178",
            "https://cdn.coinhako.com/assets/wallet-ltc-e4ce25a8fb34c45d40165b6f4eecfbca2729c40c20611acd45ea0dc3ab50f8a6.png",
            "Litecoin"
        ),
        Currency(
            "NEO",
            "USD",
            "53.1143",
            "52.7589",
            "https://cdn.coinhako.com/assets/wallet-neo-3fddd91057f65c159dbb601f503d637d144398d0573bf54d69fd6edf389827c7.png",
            "Neo"
        ),
        Currency(
            "BCH",
            "USD",
            "682.302",
            "675.966",
            "https://cdn.coinhako.com/assets/wallet-bch-b111cca7bd56007d7c8594096c6aa17d1295392a851dbb57785546f055adced4.png",
            "Bitcoin Cash"
        ),
        Currency(
            "1INCH",
            "USD",
            "3.07059",
            "3.04683",
            "https://cdn.coinhako.com/assets/wallet-1inch-24899df6c971ffd99466f8b46ceb49c60f96bb6b186dad875c56e3b2ba9a03a8.png",
            "1INCH"
        ),
        Currency(
            "AAVE",
            "USD",
            "366.146",
            "363.366",
            "https://cdn.coinhako.com/assets/wallet-aave-0eceff0513f650e657389f560a3fd485070f6a52a0ee513c503d682ef9d37b14.png",
            "Aave"
        ),
        Currency(
            "ADA",
            "USD",
            "1.73338",
            "1.72218",
            "https://cdn.coinhako.com/assets/wallet-ada-e245dd24f5bb3fd5a43f24bf3159eda6600da991b63ff076b7ba8a5965465f8d.png",
            "Cardano"
        ),
        Currency(
            "BAND",
            "USD",
            "8.26874",
            "8.21147",
            "https://cdn.coinhako.com/assets/wallet-band-72dabd201b133ac3aa69eb144f9ca9aadf98ef9333dc5947d228e8ababe1a2b8.png",
            "Band Protocol"
        ),
        Currency(
            "BNB",
            "USD",
            "354.767",
            "352.552",
            "https://cdn.coinhako.com/assets/wallet-bnb-99e7383556a6d8c5b174a4f56084bc1bbb9fbaae88fa55d0ab6f9211461f03a3.png",
            "Binance Coin"
        ),
        Currency(
            "COMP",
            "USD",
            "418.262",
            "413.749",
            "https://cdn.coinhako.com/assets/wallet-comp-198e2a18660d7ee7812cbd7bdedd68b39ef2692220c15e575656bdeaf430bf7e.png",
            "Compound"
        ),
        Currency(
            "BTC",
            "USD",
            "36143.04",
            "36137.96",
            "https://cdn.coinhako.com/assets/wallet-btc-6af73eef204cc6dccf1aedd5549390fbc44b97e0a8e825f305bd820b436002a3.png",
            "Bitcoin"
        ),
    )

    private val bSearchListExpected = listOf(
        Currency(
            "BCH",
            "USD",
            "682.302",
            "675.966",
            "https://cdn.coinhako.com/assets/wallet-bch-b111cca7bd56007d7c8594096c6aa17d1295392a851dbb57785546f055adced4.png",
            "Bitcoin Cash"
        ),
        Currency(
            "BAND",
            "USD",
            "8.26874",
            "8.21147",
            "https://cdn.coinhako.com/assets/wallet-band-72dabd201b133ac3aa69eb144f9ca9aadf98ef9333dc5947d228e8ababe1a2b8.png",
            "Band Protocol"
        ),
        Currency(
            "BNB",
            "USD",
            "354.767",
            "352.552",
            "https://cdn.coinhako.com/assets/wallet-bnb-99e7383556a6d8c5b174a4f56084bc1bbb9fbaae88fa55d0ab6f9211461f03a3.png",
            "Binance Coin"
        ),
        Currency(
            "BTC",
            "USD",
            "36143.04",
            "36137.96",
            "https://cdn.coinhako.com/assets/wallet-btc-6af73eef204cc6dccf1aedd5549390fbc44b97e0a8e825f305bd820b436002a3.png",
            "Bitcoin"
        ),
    )

    private val AAVESearchListExpected = listOf(
        Currency(
            "AAVE",
            "USD",
            "366.146",
            "363.366",
            "https://cdn.coinhako.com/assets/wallet-aave-0eceff0513f650e657389f560a3fd485070f6a52a0ee513c503d682ef9d37b14.png",
            "Aave"
        ),
    )

    @Test
    fun searchByName_isCorrect() {
        assertEquals(originalList, filterUseCase.searchByName(null, originalList))
        assertEquals(originalList, filterUseCase.searchByName("", originalList))
        assertEquals(originalList, filterUseCase.searchByName("    ", originalList))
        assertEquals(bSearchListExpected, filterUseCase.searchByName("b", originalList))
        assertEquals(AAVESearchListExpected, filterUseCase.searchByName("AAVE", originalList))
        assertEquals(emptyList<Currency>(), filterUseCase.searchByName("dgkdjgsk", originalList))
    }
}
