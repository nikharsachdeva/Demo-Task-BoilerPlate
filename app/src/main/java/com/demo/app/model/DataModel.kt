package com.demo.app.model


import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("_id")
    var id: String?,
    @SerializedName("item_taxes")
    var itemTaxes: List<Int?>?,
    @SerializedName("name")
    var name: List<String?>?,
    @SerializedName("price")
    var price: Int?,
    @SerializedName("specifications")
    var specifications: List<Specification?>?
) {
    data class Specification(
        @SerializedName("_id")
        var id: String?,
        @SerializedName("isAssociated")
        var isAssociated: Boolean?,
        @SerializedName("isParentAssociate")
        var isParentAssociate: Boolean?,
        @SerializedName("is_required")
        var isRequired: Boolean?,
        @SerializedName("list")
        var list: List<DataList?>?,
        @SerializedName("max_range")
        var maxRange: Int?,
        @SerializedName("modifierGroupId")
        var modifierGroupId: String?,
        @SerializedName("modifierGroupName")
        var modifierGroupName: String?,
        @SerializedName("modifierId")
        var modifierId: String?,
        @SerializedName("modifierName")
        var modifierName: String?,
        @SerializedName("name")
        var name: List<String?>?,
        @SerializedName("range")
        var range: Int?,
        @SerializedName("sequence_number")
        var sequenceNumber: Int?,
        @SerializedName("type")
        var type: Int?,
        @SerializedName("unique_id")
        var uniqueId: Int?,
        @SerializedName("user_can_add_specification_quantity")
        var userCanAddSpecificationQuantity: Boolean?
    ) {
        data class DataList(
            @SerializedName("_id")
            var id: String?,
            @SerializedName("is_default_selected")
            var isDefaultSelected: Boolean?,
            @SerializedName("name")
            var name: List<String?>?,
            @SerializedName("price")
            var price: Int?,
            @SerializedName("sequence_number")
            var sequenceNumber: Int?,
            @SerializedName("specification_group_id")
            var specificationGroupId: String?,
            @SerializedName("unique_id")
            var uniqueId: Int?,
            var isOptionSelected: Boolean = false
        )
    }
}