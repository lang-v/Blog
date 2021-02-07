package sl.app.module_markdown.component

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import sl.app.module_markdown.R
import sl.app.module_markdown.bean.Element
import sl.app.module_markdown.bean.TableElement
import sl.app.module_markdown.component.base.MarkDownView

/**
 * 表格
 * 基本格式：
 * |rowtitle|rowtitle|
 * |-----|:--:|
 * |data|data|
 * 【:】代表对齐方式
 */
class TableView(context: ViewGroup, private val elements: TableElement) :
    MarkDownView(context, elements) {
    private val table: ArrayList<List<String>> = ArrayList()

    init {
        //格式化数据
        elements.rowArray.forEach {
            table.add(it.split("|").toList())
        }
    }

    override fun createView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.table_view_layout, parent, false)
    }

    override fun bindView(view: View) {
        view.post {
            val tableLayout = view.findViewById<TableLayout>(R.id.table)
            for (i in table.indices) {
                if (table[i].isEmpty()) continue
                val row = TableRow(view.context)
                when (i) {
                    //作为标题栏 ，需要配置背景和字体加粗
                    0 -> {
                        for (j in table[i].indices) {
                            if (table[i][j].isEmpty()) continue
                            row.addView(
                                BoldTextView(
                                    (view as ViewGroup),
                                    Element(table[i][j])
                                ).getView()?.apply {
                                    background =
                                        ContextCompat.getDrawable(context, R.drawable.table_shape)
                                })
                        }
                    }
                    //读取单元格对齐方式【:】
                    1 -> {
                        continue
                    }
                    else -> {
                        for (j in table[i].indices) {
                            if (table[i][j].isEmpty()) continue
                            row.addView(CommonTextView(
                                (view as ViewGroup),
                                Element(table[i][j])
                            ).getView()
                                ?.apply {
                                    background = ContextCompat.getDrawable(
                                        context,
                                        R.drawable.table_shape
                                    )
                                })
                        }
                    }
                }
                tableLayout.addView(row)
                tableLayout.addView(View(view.context).apply {
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
                    setBackgroundColor(Color.BLACK)
                })
            }
        }
    }

    override fun getType(): Class<*> {
        return TableView::class.java
    }
}