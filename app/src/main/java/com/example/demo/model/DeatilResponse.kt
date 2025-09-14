package com.example.demo.model

data class DeatilResponse(
    val description: String ? = null,
    val document_id: String ? = null,
    val origin_url: String ? = null,
    val published_date: String ? = null,
    val publisher: Publisher? = null,
    val sections: List<Section>?  = null,
    val template_type: String? = null,
    val title: String ? = null,
)