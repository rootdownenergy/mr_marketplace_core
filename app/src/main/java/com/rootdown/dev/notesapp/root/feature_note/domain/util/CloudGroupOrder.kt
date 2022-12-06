package com.rootdown.dev.notesapp.root.feature_note.domain.util



sealed class CloudGroupOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): CloudGroupOrder(orderType)
    class Date(orderType: OrderType): CloudGroupOrder(orderType)
    class Color(orderType: OrderType): CloudGroupOrder(orderType)

    fun copy(orderType: OrderType): CloudGroupOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}