package com.mocomoco.tradin.model

import androidx.compose.ui.graphics.Color
import com.mocomoco.tradin.R
import com.mocomoco.tradin.presentation.theme.*


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

enum class TradeMethod(val code: Int, val id: String, val display: String, val resId: Int = 0) {
    None(0, "",""),
    Direct(1, "direct","직거래", R.drawable.ic_trade_method_direct),
    Parcel(2, "parcel","택배", R.drawable.ic_trade_method_parcel),
    Etc(3, "etc","기타", R.drawable.ic_trade_method_parcel) // todo 전용 아이콘으로 교체
}

enum class SortType(val code: Int, val display: String) {
    POPULAR(0, "인기순"),
    LATEST(1, "최신순"),
    VIEW(2, "조회순")
}

enum class FeedStatus(val code: String, val display: String, val backgroundColor: Color, val textColor: Color) {
    NONE("", "", Transparent, Transparent),
    WAIT("wait", "예약중", Orange3, Orange0),
    PROGRESS("progress", "교환가능", Mint3, Mint1)
}