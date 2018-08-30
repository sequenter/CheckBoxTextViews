package uk.co.onemandan.checkboxtextviews

interface CheckBoxTextViewsListener {

    // Called every time a CheckBox item is selected or deselected
    fun onItemSelected(items: List<String>)
}