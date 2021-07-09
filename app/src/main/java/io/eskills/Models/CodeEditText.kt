package io.eskills.Models

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.util.regex.Matcher
import java.util.regex.Pattern


class CodeEditText(context: Context?, attrs: AttributeSet?) :
    AppCompatEditText(context!!, attrs) {
    // add line number
    private val rect: Rect
    private val paint: Paint
    override fun onDraw(canvas: Canvas) {
        var baseline = baseline //
        for (i in 0 until lineCount) {
            canvas.drawText("" + (i + 1), rect.left.toFloat(), baseline.toFloat(), paint)
            baseline += lineHeight
        }
        super.onDraw(canvas)
    }

    companion object {
        //high light key words
        private val PATTERN_NUMBERS: Pattern = Pattern.compile(
            "\\b(\\d*[.]?\\d+)\\b")
        private val PATTERN_PREPROCESSOR: Pattern = Pattern.compile(
            "^[\t ]*(#define|#undef|#if|#ifdef|#ifndef|#else|#elif|#endif|" +
                    "#error|#pragma|#extension|#version|#line|#include)\\b",
            Pattern.MULTILINE)
        private val PATTERN_KEYWORDS: Pattern = Pattern.compile(
            "\\b(" +
                    "and|or|xor|for|do|while|foreach|as|return|die|exit|if|then|else|" +
                    "elseif|new|delete|try|throw|catch|finally|class|function|string|" +
                    "array|object|resource|var|bool|boolean|int|integer|float|double|" +
                    "real|string|array|global|const|static|public|private|protected|" +
                    "published|extends|switch|true|false|null|void|this|self|struct|" +
                    "char|signed|unsigned|short|long|True|False|a|address|app|applet|" +
                    "area|b|base|basefont|bgsound|big|blink|blockquote|body|br|button|" +
                    "caption|center|cite|code|col|colgroup|comment|dd|del|dfn|dir|div|" +
                    "dl|dt|em|embed|fieldset|font|form|frame|frameset|h1|h2|h3|h4|h5|h6|" +
                    "head|hr|html|htmlplus|hype|i|iframe|img|input|ins|del|isindex|kbd|" +
                    "label|legend|li|link|listing|map|marquee|menu|meta|multicol|nobr|" +
                    "noembed|noframes|noscript|ol|option|p|param|plaintext|pre|s|samp|" +
                    "script|select|small|sound|spacer|span|strike|strong|style|sub|sup|" +
                    "table|tbody|td|textarea|tfoot|th|thead|title|tr|tt|u|var|wbr|xmp" +
                    ")\\b")
        private val PATTERN_BUILTINS: Pattern = Pattern.compile(
            ("\\b(radians|degrees|sin|cos|tan|asin|acos|atan|pow|" +
                    "exp|log|sqrt|inversesqrt|abs|sign|floor|ceil|fract|mod|" +
                    "min|max|length|Math|System|out|printf|print|println|" +
                    "console|Arrays|Array|vector|List|list|ArrayList|Map|HashMap|" +
                    "dict|java|util|lang|import|from|in|charset|lang|href|name|" +
                    "target|onclick|onmouseover|onmouseout|accesskey|code|codebase|" +
                    "width|height|align|vspace|hspace|border|name|archive|mayscript|" +
                    "alt|shape|coords|target|nohref|size|color|face|src|loop|bgcolor|" +
                    "background|text|vlink|alink|bgproperties|topmargin|leftmargin|" +
                    "marginheight|marginwidth|onload|onunload|onfocus|onblur|stylesrc|" +
                    "scroll|clear|type|value|valign|span|compact|pluginspage|pluginurl|" +
                    "hidden|autostart|playcount|volume|controls|controller|mastersound|" +
                    "starttime|endtime|point-size|weight|action|method|enctype|onsubmit|" +
                    "onreset|scrolling|noresize|frameborder|bordercolor|cols|rows|" +
                    "framespacing|border|noshade|longdesc|ismap|usemap|lowsrc|naturalsizeflag|" +
                    "nosave|dynsrc|controls|start|suppress|maxlength|checked|language|onchange|" +
                    "onkeypress|onkeyup|onkeydown|autocomplete|prompt|for|rel|rev|media|direction|" +
                    "behaviour|scrolldelay|scrollamount|http-equiv|content|gutter|defer|event|" +
                    "multiple|readonly|cellpadding|cellspacing|rules|bordercolorlight|" +
                    "bordercolordark|summary|colspan|rowspan|nowrap|halign|disabled|accesskey|" +
                    "tabindex|id)\\b"))
        private val PATTERN_COMMENTS: Pattern = Pattern.compile(
            "/\\*(?:.|[\\n\\r])*?\\*/|//.*")

        fun setHighLight(str: String?): SpannableString {
            val ss = SpannableString(str)
            val ptn_color: MutableMap<Pattern, String> = HashMap<Pattern, String>()
            ptn_color[PATTERN_COMMENTS] = "#076421"
            ptn_color[PATTERN_NUMBERS] = "#c29810"
            ptn_color[PATTERN_KEYWORDS] = "#781ebe"
            ptn_color.put(PATTERN_BUILTINS, "#0a0733")
            ptn_color[PATTERN_PREPROCESSOR] = "#7c4204"
            for (ptn: Pattern in ptn_color.keys) {
                val colorStr = ptn_color[ptn]
                val matcher: Matcher = ptn.matcher(ss)
                while (matcher.find()) {
                    ss.setSpan(ForegroundColorSpan(Color.parseColor(colorStr)),
                        matcher.start(),
                        matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
            return ss
        }
    }

    init {
        rect = Rect()
        paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.GRAY
        paint.textSize = 30F
    }
}