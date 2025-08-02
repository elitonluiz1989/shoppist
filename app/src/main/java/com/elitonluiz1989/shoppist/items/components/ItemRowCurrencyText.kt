import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.elitonluiz1989.shoppist.items.components.ItemRowText
import com.elitonluiz1989.shoppist.shared.currencyFormatter
import com.elitonluiz1989.shoppist.shared.getCurrentLocale

@Composable
fun ItemRowCurrencyText(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Visible,
    modifier: Modifier = Modifier
) {
    val locale = getCurrentLocale()
    val textFormatted = currencyFormatter(text, locale)

    ItemRowText(
        text = textFormatted,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}
