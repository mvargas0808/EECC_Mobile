package Common;

import android.support.v7.app.AppCompatActivity;


public class Constants extends AppCompatActivity {

    //---- TEXTS
    public static String INTERVENTION_URGENCY = "Urgencia de intervención";

    /*--------------------
    * OBJECTS TYPES
    ---------------------*/
    public static final String ARRAYLIST = "ARRAYLIST";
    public static final String JSONObject = "JSONObject";





    /*--------------------
    * ERROR MESSAGES
    ---------------------*/

    public static String ERROR_LOAD_TABLE_DATA = "Se produjo un error cargando los datos de la tabla";
    public static String ERROR_EVALUATIONID_FROM_EVA_MENU = "No se obtuvo EvaluationID del menú de evaluaciones";

    //######### IDE
    public static String IDE_ERROR = "Favor ingrese el Índice de Corrosión y el Índice Estructural para poder realizar la evaluación";
    public static String IDE_EVALUATION_ERROR = "Favor calcule el IDE para poder realizar la evaluación";
    public static String IDE_NUMBERS_ERROR = "El Índice de corrosión y el Índice Estructural, deben ser números entre 0 y 4";

    public static String ERROR_INSERT_EVALUATION = "No fue posible insertar la evaluación";
    public static String ERROR_UPDATE_EVALUATION = "No fue posible actualizar la evaluación";

    //######### IDC
    public static String INDICATORS_ERROR = "Favor ingrese todos los indicadores para poder calcular el IDC";

    //######### IC
    public static String EVALUATE_IC_ERROR = "Favor verifique que haya calculado el IDC y el IAA antes de evaluar el IC";
    public static String IC_CALCULATE_ERROR = "El cálculo del IC no pudo ser realizado";
    public static String ERROR_INSERT_INDICATOR = "Indicador del IDC no pudo ser almacenado";
    public static String ERROR_SAVE_IC = "El cálculo del Índice de Corrosión no puede ser realizado";
    public static String ERROR_UPDATE_IC = "La actualización del Índice de Corrosión no puede ser realizado";
    public static String IC_ID_ERROR = "El Id del IC no fue encontrado";
    public static String ERROR_DISABLE_INDICATORS = "Los indicadores no pudieron ser deshabilitados";

    /*--------------------
    * SUCCESS MESSAGES
    ---------------------*/
    //######### IDE
    public static String SUCCESS_INSERT_EVALUATION = "La evaluación ha sido insertada";
    public static String SUCCESS_UPDATE_EVALUATION = "La evaluación ha sido actualizada";


    //######### IDC
    public static String SUCCESS_IDC = "El IDC ha sido calculado";
    public static String SUCCESS_SAVE_IC = "El IC ha sido calculado";
    public static String SUCCESS_UPDATE_IC = "El IC ha sido actualizado";


}
