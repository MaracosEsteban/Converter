

///////////////////////////////////////////////// MAIN PROGAM MAIN()
fun main() {
    val converter = Converter() // Creo una instancia del convertidor
    var tokens: List<String>
    lateinit var response: List<String>
    while (true) {   //
        println("Enter what you want to convert (or exit):")
        val input: String = readln() // leo la entrada
        response = converter.valInput(input)
        when (response[0]) {
            "exit" -> break
            "Parse error" -> println("Parse error")
            "Length shouldn't be negative" -> println("Length shouldn't be negative")
            "Weight shouldn't be negative" -> println("Weight shouldn't be negative")
            else -> {
                val palabras = response[0].split(" ")
                val primeraPalabra = palabras.firstOrNull()

                if (primeraPalabra == "Conversion") {
                    println(response[0])
                }
                else {
                    println(converter.ConvertirUnidad(response))
                }
            }
        }
    }
}

///////////////////////////////////////////////// ALL THE DATA CLASS


data class informacion(
    val magEntradaPlural: String,
    val magEntradaSingular: String,
    val magSalidaPlural: String,
    val magSalidaSingular: String,
    var coef_ReducUnidad: Any,        // Puede contener un coeficiente Double o una funcion (Double) -> Double
    var cof_Unidad_a_Magnitud: Any,   // Puede contener un coeficiente Double o una funcion (Double) -> Double
)


/**
 * Cada instancia de esta clase contine los datos para
 * convertir de una magnitud a otra
 *
 */
data class Magnitud(
    var listaVariantesNombreMagnitud: List<String>,// aqui figuran todas las variantes de los nombres en minúsculas
    var magnitudEntradaPlural: String,
    var magnitudEntradaSingular: String,
    var coef_ReducUnidad: Any,
    var cof_Unidad_a_Magnitud: Any,
)


//////////////////////////////////////////////////////// CLASS REPRESENTING THE CONVERTER
class Converter() {

    var magDistancias: MutableList<Magnitud> = mutableListOf()

    var magPeso: MutableList<Magnitud> = mutableListOf()

    var magTemp: MutableList<Magnitud> = mutableListOf()

    init {
        //Ingreso las magnitudes de distancias
        magDistancias.add(Magnitud(listOf("m", "meter", "meters"), "meters", "meter", 1.0, 1.0))
        magDistancias.add(
            Magnitud(
                listOf("km", "kilometers", "kilometer"), "kilometers", "kilometer", 1000.0, (1.0 / 1000)
            )
        )
        magDistancias.add(
            Magnitud(
                listOf("cm", "centimeter", "centimeters"), "centimeters", "centimeter", 0.01, (1.0 / 0.01)
            )
        )
        magDistancias.add(
            Magnitud(
                listOf("mm", "millimeter", "millimeters"), "millimeters", "millimeter", 0.001, (1.0 / 0.001)
            )
        )
        magDistancias.add(Magnitud(listOf("mi", "mile", "miles"), "miles", "mile", 1609.35, (1.0 / 1609.35)))
        magDistancias.add(Magnitud(listOf("yd", "yard", "yards"), "yards", "yard", 0.9144, (1.0 / 0.9144)))
        magDistancias.add(Magnitud(listOf("ft", "foot", "feet"), "feet", "foot", 0.3048, (1.0 / 0.3048)))
        magDistancias.add(Magnitud(listOf("in", "inch", "inches"), "inches", "inch", 0.0254, (1.0 / 0.0254)))
        // meters,kilometers,centimeters,millimeters,miles,yards,feet,inches

//            One meter equals 1 meter.
//            One kilometer equals 1000 meters.
//            One centimeter equals 0.01 meters.
//            One millimeter equals 0.001 meters.
//            One mile equals 1609.35 meters.
//            One yard equals 0.9144 meters.
//            One foot equals 0.3048 meters.
//            One inch equals 0.0254 meters.


        //ingreso las magnitudes de peso
        magPeso.add(Magnitud(listOf("g", "gram", "grams"), "grams", "gram", 1.0, 1.0))
        magPeso.add(Magnitud(listOf("kg", "kilogram", "kilograms"), "kilograms", "kilogram", 1000.0, (1.0 / 1000.0)))
        magPeso.add(Magnitud(listOf("mg", "milligram", "milligrams"), "milligrams", "milligram", 0.001, (1.0 / 0.001)))
        magPeso.add(Magnitud(listOf("lb", "pound", "pounds"), "pounds", "pound", 453.592, (1.0 / 453.592)))
        magPeso.add(Magnitud(listOf("oz", "ounce", "ounces"), "ounces", "ounce", 28.3495, (1.0 / 28.3495)))
        //grams,kilograms",milligrams,pounds,ounces ,

//        One meter equals 1 meter.
//        One kilometer equals 1000 meters.
//        One centimeter equals 0.01 meters.
//        One millimeter equals 0.001 meters.
//        One mile equals 1609.35 meters.
//        One yard equals 0.9144 meters.
//        One foot equals 0.3048 meters.
//        One inch equals 0.0254 meters.

        // ingreso las magnitudes de temperatura
        magTemp.add(
            Magnitud(
                listOf("degree Celsius", "degrees Celsius", "celsius", "dc", "c"),
                "degrees Celsius",
                "degree Celsius",
                ::CelsiusToCelsius,
                ::CelsiusToCelsius
            )
        )

        magTemp.add(
            Magnitud(
                listOf("degree Fahrenheit", "degrees Fahrenheit", "fahrenheit", "df", "f"),
                "degrees Fahrenheit",
                "degree Fahrenheit",
                ::FahrenheitToCelsius,
                ::CelsiusToFahrenheit
            )
        )

        magTemp.add(
            Magnitud(
                listOf("kelvin", "kelvins", "k"), "kelvins", "kelvin", ::KelvinsToCelsius, ::CelsiusToKelvins
            )
        )


    }


    //  Formulae for converting one unit of temperature to another
    fun FahrenheitToCelsius(fahr: Double) = (fahr - 32.0) * (5.0 / 9.0)
    fun CelsiusToFahrenheit(cel: Double) = cel * (9.0 / 5.0) + 32.0

    fun CelsiusToKelvins(cels: Double) = cels + 273.15
    fun KelvinsToCelsius(kelv: Double) = kelv - 273.15

    fun KelvinsToFahrenheit(kelv: Double) = kelv * (9.0 / 5.0) - 459.67
    fun FahrenheittoKelvins(fahr: Double) = (fahr + 459.67) * (5.0 / 9.0)

    fun CelsiusToCelsius(cels: Double) = cels


    /*
    Funcion de clase que valida las entradas,
     */

    fun valInput(input: String?): List<String> {  // devuelve exit, parsse error o la lista validada
        //devuelve el mansaje de error correspondiente de lo contrario ok o exit
        var tokens = input?.split(" ")


        if (tokens == null) {
            return listOf("Parse error")
        }


        if (tokens.size == 1 && tokens[0].lowercase() == "exit") {
            return listOf("exit")
        }


        // filtra las palabras degrees, despues de eso el tratamiento es exactamente igual
        tokens = tokens.filter { it -> it.lowercase() != "degree" && it.lowercase() != "degrees" }






        if (tokens.size != 4) {
            return listOf("Parse error")
        }
        //si el primero o el tercero no se pueden  convertir a double
        if (!convertibleDouble(tokens[0])) {
            return listOf("Parse error")
        }


        // que la segunda o tercer palabras  (magnitudes ) no figuran en "conocimiento"
        if (!(ExisteMagEntradaY_Salida(tokens[1], tokens[3]))) {
            val p1 = if (ExisteMagEntradaY_Salida(tokens[1], tokens[1])) {
                obtenerPlural(tokens[1])
            }
            else "???"
            val p2 = if (ExisteMagEntradaY_Salida(tokens[3], tokens[3])) {
                obtenerPlural(tokens[3])
            }
            else "???"
            return listOf("Conversion from $p1 to $p2 is impossible")
        }

        //si el número es negativo me fijo si la magnitd es de distancia
        if (tokens[0].toDouble() < 0 && magIsLength(tokens[1])) {
            return listOf("Length shouldn't be negative")
        }


        // si el número es negativo me fijo si la magnitud es de peso
        if (tokens[0].toDouble() < 0 && magIsWeight(tokens[1])) {
            return listOf("Weight shouldn't be negative")
        }

        // validar que la tercer palabara tines que ser "to"(o varienate de escritura)
        return tokens  // devuelvo los tokens validados
    }


    fun obtenerPlural(magAChekear: String): String {
        //primero me fijo si la magnitud está con las de distancia
        for (mag in magDistancias) {
            if (mag.listaVariantesNombreMagnitud.contains(magAChekear.lowercase())) {
                return mag.magnitudEntradaPlural
            }
        }
        //me fijo si la magnitud está entre las magnitudes de peso
        for (mag in magPeso) {
            if (mag.listaVariantesNombreMagnitud.contains(magAChekear.lowercase())) {
                return mag.magnitudEntradaPlural
            }
        }
        //me fijo si la magnitud está entre las magnitudes de peso
        for (mag in magTemp) {
            if (mag.listaVariantesNombreMagnitud.contains(magAChekear.lowercase())) {
                return mag.magnitudEntradaPlural
            }
        }


        throw IllegalArgumentException("La funcion ha sido llamada con una magnitud que no existeee")
    }


    fun magIsLength(cadenaMagnitud: String): Boolean = magDistancias.any { mag ->
        mag.listaVariantesNombreMagnitud.contains(cadenaMagnitud.lowercase())
    }


    fun magIsWeight(cadenaMagnitud: String): Boolean = magPeso.any { mag ->
        mag.listaVariantesNombreMagnitud.contains(cadenaMagnitud.lowercase())
    }


    fun convertibleDouble(cadena: String): Boolean {
        return try {
            cadena.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    // las magnitudoes de entrada y salida tienen que existir las dos y estar las dos en magDistancia o las dos en magnitudes peso
    fun ExisteMagEntradaY_Salida(magEntrada: String, magSalida: String): Boolean {
        val magEn = magEntrada.lowercase()
        val magSa = magSalida.lowercase()

        var magEnFigura = false
        var magSaFigura = false

        //Primero me fijo si las dos estan en las magnitudes de distancia
        for (mag in magDistancias) {
            if (mag.listaVariantesNombreMagnitud.contains(magEn)) {
                magEnFigura = true
            }
            if (mag.listaVariantesNombreMagnitud.contains(magSa)) {
                magSaFigura = true
            }
        }
        if (magEnFigura && magSaFigura) {
            return true
        }

        //Vuelvo a poner estas dos banderas en falso
        magEnFigura = false
        magSaFigura = false

        //me fijo si las dos estan en las magnitudes de peso
        for (mag in magPeso) {
            if (mag.listaVariantesNombreMagnitud.contains(magEn)) {
                magEnFigura = true
            }
            if (mag.listaVariantesNombreMagnitud.contains(magSa)) {
                magSaFigura = true
            }
        }
        if (magEnFigura && magSaFigura) {
            return true
        }

        //Vuelvo a poner estas dos banderas en falso
        magEnFigura = false
        magSaFigura = false


        //me fijo si las dos estan en las magnitudes de temperatura
        for (mag in magTemp) {
            if (mag.listaVariantesNombreMagnitud.contains(magEn)) {
                magEnFigura = true
            }
            if (mag.listaVariantesNombreMagnitud.contains(magSa)) {
                magSaFigura = true
            }
        }
        if (magEnFigura && magSaFigura) {
            return true
        }






        return false
    }


    fun obternerInformacion(magEntrada: String, magSalida: String): informacion {
        // println("la magnitud de entrada es $magEntrada")
        // println("la magnitud de salida  es $magSalida")

        //val info = informacion("", "", "", "", 1.0, 1.0)
        var magEnFigura = false
        var indiceMagEntrada = 0 // valor arbitrario para inicializarlo
        var magSaFigura = false
        var indiceMagSalida = 0  // valor arbitrario para inicializarlo

        //Primero me fijo si las dos estan en las magnitudes de distancia
        for (x in 0..magDistancias.lastIndex) {
            if (magDistancias[x].listaVariantesNombreMagnitud.contains(magEntrada)) {
                magEnFigura = true
                indiceMagEntrada = x
            }
            if (magDistancias[x].listaVariantesNombreMagnitud.contains(magSalida)) {
                magSaFigura = true
                indiceMagSalida = x
            }
        }
        if (magEnFigura && magSaFigura) {


            return informacion(
                magDistancias[indiceMagEntrada].magnitudEntradaPlural,
                magDistancias[indiceMagEntrada].magnitudEntradaSingular,
                magDistancias[indiceMagSalida].magnitudEntradaPlural,
                magDistancias[indiceMagSalida].magnitudEntradaSingular,
                magDistancias[indiceMagEntrada].coef_ReducUnidad,
                magDistancias[indiceMagSalida].cof_Unidad_a_Magnitud,
            )
        }
        //reseteo las variable
        magEnFigura = false
        indiceMagEntrada = 0 // valor arbitrario para inicializarlo
        magSaFigura = false
        indiceMagSalida = 0  // valor arbitrario para inicializarlo
        //Ahora me fijo si las dos estan en las magnitudes de peso

        for (x in 0..magPeso.lastIndex) {
            if (magPeso[x].listaVariantesNombreMagnitud.contains(magEntrada)) {
                magEnFigura = true
                indiceMagEntrada = x
            }
            if (magPeso[x].listaVariantesNombreMagnitud.contains(magSalida)) {
                magSaFigura = true
                indiceMagSalida = x
            }
        }
        if (magEnFigura && magSaFigura) {
            return informacion(
                magPeso[indiceMagEntrada].magnitudEntradaPlural,
                magPeso[indiceMagEntrada].magnitudEntradaSingular,
                magPeso[indiceMagSalida].magnitudEntradaPlural,
                magPeso[indiceMagSalida].magnitudEntradaSingular,
                magPeso[indiceMagEntrada].coef_ReducUnidad,
                magPeso[indiceMagSalida].cof_Unidad_a_Magnitud,
            )
        }
        //reseteo las variable
        magEnFigura = false
        indiceMagEntrada = 0 // valor arbitrario para inicializarlo
        magSaFigura = false
        indiceMagSalida = 0  // valor arbitrario para inicializarlo
        //Ahora me fijo si las dos estan en las magnitudes de temperatura
        for (x in 0..magTemp.lastIndex) {
            if (magTemp[x].listaVariantesNombreMagnitud.contains(magEntrada)) {
                magEnFigura = true
                indiceMagEntrada = x
            }
            if (magTemp[x].listaVariantesNombreMagnitud.contains(magSalida)) {
                magSaFigura = true
                indiceMagSalida = x
            }
        }
        if (magEnFigura && magSaFigura) {
            return informacion(
                magTemp[indiceMagEntrada].magnitudEntradaPlural,
                magTemp[indiceMagEntrada].magnitudEntradaSingular,
                magTemp[indiceMagSalida].magnitudEntradaPlural,
                magTemp[indiceMagSalida].magnitudEntradaSingular,
                magTemp[indiceMagEntrada].coef_ReducUnidad,
                magTemp[indiceMagSalida].cof_Unidad_a_Magnitud,
            )
        }


        throw IllegalArgumentException("La funcion has sido llamada con parámetros inválidos222")
        // esto no puede suseder nunca poque los argumentos fueron chequedos previamente
    }


    fun ConvertirUnidad(tokens: List<String>): String {

        val infoRes = obternerInformacion(tokens[1].lowercase(), tokens[3].lowercase())

        infoRes.coef_ReducUnidad
        infoRes.cof_Unidad_a_Magnitud


        lateinit var unEntrada: String
        lateinit var unSalida: String
        val cantEntrada = tokens[0].toDouble()

        // Tener en cuenta que también puede ser una fórmula
        var cantSalida = 0.00
        if (infoRes.coef_ReducUnidad is Double) {
            cantSalida = infoRes.coef_ReducUnidad as Double * infoRes.cof_Unidad_a_Magnitud as Double * cantEntrada
        }
        else {
            var aux = infoRes.coef_ReducUnidad

            val fun1 = infoRes.coef_ReducUnidad as (Double) -> Double
            val fun2 = infoRes.cof_Unidad_a_Magnitud as (Double) -> Double

            cantSalida = fun2(fun1(cantEntrada))
        }




        if (cantEntrada == 1.0) {
            unEntrada = infoRes.magEntradaSingular
        }
        else {
            unEntrada = infoRes.magEntradaPlural
        }

        if (cantSalida == 1.00) {
            unSalida = infoRes.magSalidaSingular
        }
        else {
            unSalida = infoRes.magSalidaPlural
        }

        return "$cantEntrada $unEntrada is $cantSalida $unSalida"
    }
}