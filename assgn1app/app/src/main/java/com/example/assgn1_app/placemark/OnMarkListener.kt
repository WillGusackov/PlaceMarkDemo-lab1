package com.example.assgn1_app.placemark

interface OnMarkListener {
    fun onMarkClick(mark: PlacedMark)
    fun onMarkLongClick(mark: PlacedMark): Boolean
}
