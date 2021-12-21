package com.example.email.model.payload;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

   private final String data;
   private final Boolean success;
   private final String timestamp;
   private final String path;
   private final Object message;
   private final int responseCode;

   public ApiResponse(Boolean success, Object message, int responseCode, String path) {
       this.timestamp = Instant.now().toString();
       this.data = null;
       this.success = success;
       this.path = path;
       this.message = message;
       this.responseCode = responseCode;
   }

   public ApiResponse(Boolean success, Object message, int responseCode) {
       this.timestamp = Instant.now().toString();
       this.data = null;
       this.success = success;
       this.path = null;
       this.message = message;
       this.responseCode = responseCode;
   }

   public String getData() {
       return data;
   }

   public Boolean getSuccess() {
       return success;
   }

   public String getTimestamp() {
       return timestamp;
   }
   
   public Object getMessage() {
       return message;
   }

   public int getResponseCode() {
       return responseCode;
   }

   public String getPath() {
       return path;
   }
}
