package stud11417031.develops.projecttestwithlaravel.storage;

import android.content.Context;
import android.content.SharedPreferences;

import stud11417031.develops.projecttestwithlaravel.model.User;

public class SharedPrefManager {

    public static final String SHARED_PREF_NAME = "my_shared_pref";
    private static SharedPrefManager mInstance;
    private Context mContext;
    public static boolean LOGGED_IN=false;
    private SharedPrefManager(Context ctx) {
        this.mContext = ctx;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("firstname", user.getFirstname());
        editor.putString("lastname", user.getLastname());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", null) != null;
    }
    public User getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("username",null),
                sharedPreferences.getString("firstname",null),
                sharedPreferences.getString("lastname",null),
                sharedPreferences.getString("image",null)
        );
    }
    public void clear(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
    }
}
