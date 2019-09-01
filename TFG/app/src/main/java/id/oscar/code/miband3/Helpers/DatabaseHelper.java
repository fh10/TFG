package id.oscar.code.miband3.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="database.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,5 );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE usuarios (ID INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT, password TEXT, nombre TEXT, apellidos TEXT, fecha_nac TEXT, sexo INTEGER, nivel INTEGER," +
                " peso REAL, altura REAL, pasos INTEGER, calorias INTEGER, recorrido INTEGER, zancada REAL)");
        sqLiteDatabase.execSQL("CREATE TABLE historico (ID INTEGER PRIMARY  KEY AUTOINCREMENT, userId INTEGER, fecha TEXT, pasos INTEGER, calorias INTEGER, recorrido INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE imagenes (ID INTEGER PRIMARY  KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS usuarios");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS historico");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS imagenes");
        onCreate(sqLiteDatabase);
    }

    public long addUser(String user, String password, String nombre, String apellidos, String fecha, int sexo, float peso,float altura)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int nivel = 0,calorias = 0,recorrido = 0,pasos = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        contentValues.put("nombre",nombre);
        contentValues.put("apellidos",apellidos);
        contentValues.put("fecha_nac",fecha);
        contentValues.put("peso",peso);
        contentValues.put("altura",altura);
        contentValues.put("sexo",sexo);
        contentValues.put("nivel",nivel);

        if(sexo == 0)
        {
            contentValues.put("zancada",altura*0.413);
        }
        else
        {
            contentValues.put("zancada",altura*0.415);
        }

        contentValues.put("pasos",pasos);
        contentValues.put("recorrido",recorrido);
        contentValues.put("calorias",calorias);
        long res = db.insert("usuarios",null,contentValues);
        db.close();
        return  res;
    }

    public long addEjercicio(Integer user, String username, String fecha, int pasos, int recorrido,int calorias)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId",user);
        contentValues.put("fecha",fecha);
        contentValues.put("pasos",pasos);
        contentValues.put("recorrido",recorrido);
        contentValues.put("calorias",calorias);
        long res = db.insert("historico",null,contentValues);
        Usuario usuario = getUser(username);
        usuario.setPasos(usuario.getPasos()+pasos);
        usuario.setRecorrido(usuario.getRecorrido()+recorrido);
        usuario.setCalorias(usuario.getCalorias()+calorias);
        actualizarUsuario(usuario);
        db.close();
        return  res;
    }

    public long actualizarUsuario (Usuario usuario)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{usuario.getUsername()};
        ContentValues cv = new ContentValues();
        cv.put("username",usuario.getUsername());
        cv.put("password",usuario.getPassword());
        cv.put("nombre",usuario.getNombre());
        cv.put("apellidos",usuario.getApellidos());
        cv.put("fecha_nac",usuario.getFecha_nac());
        cv.put("peso",usuario.getPeso());
        cv.put("altura",usuario.getAltura());
        cv.put("sexo",usuario.getSexo());
        cv.put("nivel",usuario.getNivel());
        cv.put("zancada",usuario.getAltura()*0.415);
        cv.put("pasos",usuario.getPasos());
        cv.put("recorrido",usuario.getRecorrido());
        cv.put("calorias",usuario.getCalorias());
        long res = db.update("usuarios", cv, "username=?", args);
        return res;
    }

    public boolean checkUser(String username, String password)
    {
        String[] columns = {"ID"};
        SQLiteDatabase db = getReadableDatabase();
        String selection = "username=?" + " and " + "password=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query("usuarios",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public Usuario getUser(String username)
    {
        SQLiteDatabase db = getReadableDatabase();
        String selection = "username=?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query("usuarios",null,selection,selectionArgs,null,null,null);
        Usuario user = new Usuario();
        cursor.moveToFirst();
        user.setId(cursor.getInt(0));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        user.setNombre(cursor.getString(3));
        user.setApellidos(cursor.getString(4));
        user.setFecha_nac(cursor.getString(5));
        user.setSexo(cursor.getInt(6));
        user.setNivel(cursor.getInt(7));
        user.setPeso(cursor.getFloat(8));
        user.setAltura(cursor.getFloat(9));
        user.setPasos(cursor.getInt(10));
        user.setCalorias(cursor.getInt(11));
        user.setRecorrido(cursor.getInt(12));
        user.setZancada(cursor.getInt(13));
        cursor.close();
        db.close();
        return user;
    }

    public String getDescripcion(String nombre)
    {
        SQLiteDatabase db = getReadableDatabase();
        String selection = "nombre=?";
        String[] selectionArgs = { nombre };
        Cursor cursor = db.query("imagenes",null,selection,selectionArgs,null,null,null);
        cursor.moveToFirst();
        String descripcion = cursor.getString(2);
        cursor.close();
        db.close();
        return descripcion;
    }

    public Cursor historicoEjercicios(String user)
    {
        SQLiteDatabase db = getReadableDatabase();
        String selection = "userId =?";
        String[] selectionArgs = { user };
        Cursor cursor = db.query("historico",null,selection,selectionArgs,null,null,"ID DESC");
        int count = cursor.getCount();
        db.close();
        return cursor;
    }

    public long anyadir_descripcion(String nombre, String descripcion)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre",nombre);
        contentValues.put("descripcion",descripcion);
        long res = db.insert("imagenes",null,contentValues);
        db.close();
        return  res;
    }

    public void anyadir_Descripciones()
    {
        String descripcion = "Túmbate en el suelo boca arriba. Pon las manos en la cabeza y coloca las piernas para que exista un ángulo" +
                "de 90 graods en las rodillas. Para realizar un abdominal tienes que llevar el torso lo más cerca de las rodillas que puedas pero " +
                "sin levantar la espalda del suelo. Aprieta el abdomen mientras lo haces para que el efecto sea mayor. Recuerda que los pies no deben moverse del suelo.";

        anyadir_descripcion("abdominales1",descripcion);

        descripcion = "Túmbate en el suelo boca arriba. Pon las manos en la cabeza y levanta las piernas con las rodillas dobladas. Comenzaremos" +
                "doblando la rodilla izquierda sobre la derecha como en la imagen se puede apreciar. El ejercicio consite en llevar el torso hacia el lado de la pierna" +
                "que hemos doblado sin levantar la espalda del suelo. ";

        anyadir_descripcion("abdominales2",descripcion);

        descripcion="Colócate boca abajo con las piernas estiradas. A continuación levanta el cuerpo con los brazos extendidos, como si fueras a realizar una flexión." +
                "Ahora dobla los brazos colocando todo el peso del cuerpo en los antebrazos con las piernas en puntillas.";

        anyadir_descripcion("abdominales3",descripcion);

        descripcion = "Colócate con los pies a la misma distancia que los hombros y mirando al frente y con los brazos extendidos. El ejercicio consite en el descenso doblando las rodillas y" +
                " sacando la pelvis hacia atrás sin perder la mirada al frente. Es muy importante que tus rodillas nunca jamás deben pasar la linea vertical marcada por la punta de tus pies. " +
                "El peso debe estar distribuido en ambos pies por igual. Se recomienda sacar pecho y apretar los abdominales y los glúteos, así se reforzará el efecto del ejercicio y darás mayor estabilidad a tu columna.";

        anyadir_descripcion("sentadilla1",descripcion);

        descripcion = "Se apoya la espalda por completo en la pared. También apoyamos la cabeza. Adelantamos los pies, y los separamos un poco. Bajamos el cuerpo hasta que las rodillas y caderas formen un ángulo recto";

        anyadir_descripcion("sentadilla2",descripcion);

        descripcion = "Colócate inicialmente de pie, con las piernas ligeramente separadas del ancho de la cadera. Al comenzar el movimiento debemos inspirar y efectuar una zancada, es decir dar un paso adelante con una pierna manteniendo el torso lo más recto posible." +
                "La pierna desplazada hacia adelante debe flexionarse por su rodilla hasta que el muslo quede paralelo al suelo y la rodilla flexionada forme con la pierna un ángulo de 90 grados. La pierna que no se desplaza debe quedar anclada con el pie al suelo pero debe descender " +
                "hacia el mismo por la rodilla.";

        anyadir_descripcion("sentadilla3",descripcion);

        descripcion = "Colócate boca arriba con las rodillas formando 90 grados y con los brazos extendidos verticalmente. Flexiona la pelvis hacia arriba, iniciando con un movimiento de retroversión de pelvis y articulando la columna vertebral y flexionando vértebra a vértebra, " +
                "hasta alcanzar la posición de puente, con apoyo sobre las escápulas (no sobre los hombros ni el cuello).";

        anyadir_descripcion("puente1",descripcion);

        descripcion = "Acuéstate sobre tu espalda con los brazos extendidos verticalmente hacia abajo terminando en la cadera, las rodillas flexionadas y los pies apoyados sobre el suelo. Manten los pies separados a la distancia de entre las caderas y los brazos a los costados, palmas contra el piso. Esta es tu posición inicial." +
                " Estabilica el tronco y presiona los talones contra el piso, llevando las caderas hacia arriba y terminando el movimiento con contracción del trasero, asegurándose de no usar la zona lumbar.";

        anyadir_descripcion("puente2",descripcion);

        descripcion = "Acuéstate sobre tu espalda con los brazos extendidos horizontalmente, las rodillas flexionadas y los pies apoyados sobre el suelo. Manten los pies separados a la distancia de entre las caderas y los brazos a los costados, palmas contra el piso. Esta es tu posición inicial." +
                " Estabilica el tronco y presiona los talones contra el piso, llevando las caderas hacia arriba y terminando el movimiento con contracción del trasero, asegurándose de no usar la zona lumbar.";

        anyadir_descripcion("puente3",descripcion);

        descripcion = "Acuéstate boca abajo. Coloca las palmas de las manos en el suelo a la altura de los hombros, ligeramente más abiertos que el ancho de tus hombros." +
                "Mantén tu cuerpo erguido. Levanta el cuerpo hacia arriba y ve enderezando los brazos, procura mantener una postura erguida. El cuerpo debe apoyarse únicamente sobre las manos y los dedos de los pies, manteniendo la posición erguida todo el tiempo." +
                "Bajamos el cuerpo doblando los brazos, volvemos a la posición inicial extendiendo los brazos";

        anyadir_descripcion("brazos1",descripcion);

        descripcion="Nos sentaremos en una  silla colocando las manos a los lados del cuerpo con los dedos mirando hacia delante, colocando los pies sobre otra silla situada en frente, donde tengamos estabilidad." +
                "Una vez estemos en posición, nos sostenemos sobre los brazos bajando lentamente los gluteos hasta que tus codos queden lo más cerca de un ángulo de 90 grados. Empujamos ejerciendo fuerza con los triceps para volver a la posición inicial de manera" +
                " que los brazos queden estirados pero sin forzar los codos, así evitaremos perder tensión y hacernos daño.";

        anyadir_descripcion("brazos2",descripcion);

        descripcion="En primer lugar, coloca la esterilla en el suelo y túmbate encima boca abajo." +
                "En el suelo, apoya el peso del cuerpo sobre los antebrazos y los dedos de los pies. Los brazos deben permanecer flexionados y debajo de los hombros." +
                "Intenta mantener los brazos hacia afuera y bien rectos. Los dedos índices tienen que estar apoyados en el suelo." +
                "Aprieta los omoplatos y extiende la columna vertebral, de manera que el cuerpo quede totalmente erguido." +
                "Presiona los muslos hacia arriba y estira los talones.";

        anyadir_descripcion("brazos3",descripcion);
    }
}