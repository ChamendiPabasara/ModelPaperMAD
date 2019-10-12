package com.example.modelpaper;

import android.provider.BaseColumns;

public final class UserProfile {

   private UserProfile(){}

   public static class Users implements BaseColumns{

      public static final String TABLE_NAME = "users";
      public static final String COL_NAME = "userName";
      public static final String COL_PASSWORD = "password";
      public static final String COL_DOB = "dateOfBirth";
      public static final String COL_GENDER = "Gender";

   }


}
