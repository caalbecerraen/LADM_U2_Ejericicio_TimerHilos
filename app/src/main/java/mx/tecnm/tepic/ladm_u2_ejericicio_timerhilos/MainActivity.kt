package mx.tecnm.tepic.ladm_u2_ejericicio_timerhilos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var ent=true
    var contadorTimer=0
    var contadorHilo=0
    val LAPSO=2000
    val TIEMPOTOTAL=20000
    var hilito=Hilo(this)
     //Sintaxis del timer,TIEMPOTORAL Y LAPSO en MILISEGUNDOS
        var timer =object:CountDownTimer(TIEMPOTOTAL.toLong(),LAPSO.toLong()){
        override fun onTick(p0: Long){
        //On tick se  ejecuta cuando el tiempo "Lapso llega a ser 0"
            contadorTimer++
            text_2.text="Timer ${contadorTimer}"
        }
        override fun onFinish(){
            //On fish se ejecutara cuando el tiempo TOTAL se hace 0  una "
        start()
        }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    btn_1.setOnClickListener {
        if(ent){
            try{
                hilito.start()
                ent=false
            }catch (e:Exception) {
            AlertDialog.Builder(this).setMessage("El hilo no se puede volver a ejecutar")
                .setTitle("Aviso").show()
        }
        }else{
            try{
                contadorHilo = 0
                hilito.reepHilo()
                hilito.pausar()
                hilito.pausar()
            }catch (e:Exception) {
                AlertDialog.Builder(this).setMessage("El hilo no se puede volver a ejecutar")
                    .setTitle("Aviso").show()
            }
        }
    }
    btn_2.setOnClickListener {
            timer.start()
    }
        btn_terminar.setOnClickListener {
            hilito.terminarHilo()

        }
        btn_pausar.setOnClickListener {
            hilito.pausar()
        }
    }
}

//CLASE HILO
class Hilo(p:MainActivity):Thread(){
    var puntero = p//Existe solo en esta linea.
    var mantener = true
    var despausado=true
    fun pausar(){
        despausado=!despausado
    }
    fun reepHilo(){
        mantener=true
    }
    fun terminarHilo(){
        mantener=false
    }
    override fun run(){
        super.run()
        //Realmente se ejecuta una vez en segundo plano.
        while(mantener) {
            if (despausado) {
                puntero.contadorHilo++
                puntero.runOnUiThread {//PREGUNTA DE EXAMEN, PARA QUE SIRV ESTO <--
                    //Se debe usar runOnUiThread debido a que la GUI le pertenece al Main activity.
                    //Kotlin no permite de manera directa otra clase que modifique otra interfaz a la que
                    //no pertenece, tal como es el Hilo y la main Activity.
                    puntero.text_1.text = "Hilo: " + puntero.contadorHilo
                }

            }
            sleep(2000)
        }
        sleep(2000)
    }
    //Metodo similar al ONTICK, es decir esta siempre en ejecucion, siempre que se cicle
    //Realmente run solo se ejecuta 1 vez en 2do plano.
    //AL HILO LE AFECTA EL STRESS
}