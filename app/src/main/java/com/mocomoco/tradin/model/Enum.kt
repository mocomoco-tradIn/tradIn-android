package com.mocomoco.tradin.model

import com.mocomoco.tradin.R


enum class Category(val code: Int, val display: String, val iconResId: Int) {
    None(0, "", 0),
    Cloth(1, "의류", R.drawable.ic_category_cloth),
    Book(2, "서적/문구", R.drawable.ic_category_book),
    Hobby(3, "취미", R.drawable.ic_category_hobby),
    Electronic(4, "전자제품", R.drawable.ic_category_electronic),
    Stuff(5, "잡화", R.drawable.ic_category_stuff),
    Idol(6, "아이돌 굿즈", R.drawable.ic_category_idol),
    Ticket(7, "티켓/교환권", R.drawable.ic_category_ticket),
    Etc(8, "기타", R.drawable.ic_category_etc),
}

enum class TradeMethod(val code: Int, val display: String) {
    None(0, ""),
    Direct(1, "직거래"),
    Parcel(2, "택배"),
    Etc(3, "기타")
}

enum class SortType(val code: Int, val display: String) {
    POPULAR(0, "인기순"),
    LATEST(1, "최신순"),
    VIEW(2, "조회순")
}

enum class FeedStatus(val code: String, val display: String) {
    NONE("", ""),
    WAIT("wait", "예약중"),
    PROGRESS("progress", "교환가능")
}