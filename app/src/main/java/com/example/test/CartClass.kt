import com.google.gson.annotations.SerializedName

data class CartClass(
    @SerializedName("id") // @ - Аннотация
    val id: Int, // Переменная

    @SerializedName("dish_id")
    val dishId: Int,

    @SerializedName("category")
    val cat: String,

    @SerializedName("name")
    val namedish: String,

    @SerializedName("description")
    val desc: String,

    @SerializedName("price")
    val pr: Float,

    @SerializedName("img_url")
    val ic: String,

    @SerializedName("token")
    val token: Int


)
