package fi.jamk.sumcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    // clicked number, like 55
    private var number: Int = 0

    // calculated sum
    private var sum: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberInput(view: View) {
        // view is a button (pressed one) get text and convert to Int
        val digit = (view as Button).text.toString().toInt()
        typer.append(digit.toString())

    }
    fun onOperator(view: View) {
        typer.append((view as Button).text)
    }
    fun onEqual(view: View) {
        val txt = typer.text.toString()
        val expression = ExpressionBuilder(txt).build()
        try{
            // Calculate the result and display
            val result = expression.evaluate()
            txtOutput.text = result.toString()
        }catch (ex: ArithmeticException) {
            typer.text = "Error"
        }
    }
    fun onClear(view: View){
        typer.text=""
        txtOutput.text="0"
    }

}
