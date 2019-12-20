package es.architectcoders.mascotas.model




data class ad (
    val title:String="",
    val description:String="",
    val prize:Int=0,
    val currency:String,
    val category: String ="",
    var imgUriList:ArrayList<String>
)