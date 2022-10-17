package com.bayu.nestedcheckbox

data class Helpdesk(
    val id: String,
    val name: String,
    var isChecked: Boolean = false,
    val subCategories: ArrayList<SubHelpdesk>,
) {
    data class SubHelpdesk(
        val id: String,
        val name: String,
        val parentId: String,
        var isChecked: Boolean = false,
    )
}