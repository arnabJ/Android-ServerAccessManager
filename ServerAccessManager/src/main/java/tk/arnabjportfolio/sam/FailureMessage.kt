package tk.arnabjportfolio.sam

enum class FailureMessage(val msg: String) {
    RequestFailed("Failed"),
    ArraysSizeMismatch("Sizes of mAttributes & mValues do not match")
}