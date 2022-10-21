/*
 * kabuステーションAPI
 * # 定義情報   REST APIのコード一覧、エンドポイントは下記リンク参照     - [REST APIコード一覧](../ptal/error.html)
 *
 * OpenAPI spec version: 1.5
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
/**
 * TokenSuccess
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-09-20T16:10:10.621Z[GMT]")
public class TokenSuccess {
  @SerializedName("ResultCode")
  private Integer resultCode = null;

  @SerializedName("Token")
  private String token = null;

  public TokenSuccess resultCode(Integer resultCode) {
    this.resultCode = resultCode;
    return this;
  }

   /**
   * 結果コード&lt;br&gt;0が成功。それ以外はエラーコード。
   * @return resultCode
  **/
  @Schema(example = "0", description = "結果コード<br>0が成功。それ以外はエラーコード。")
  public Integer getResultCode() {
    return resultCode;
  }

  public void setResultCode(Integer resultCode) {
    this.resultCode = resultCode;
  }

  public TokenSuccess token(String token) {
    this.token = token;
    return this;
  }

   /**
   * APIトークン
   * @return token
  **/
  @Schema(example = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", description = "APIトークン")
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenSuccess tokenSuccess = (TokenSuccess) o;
    return Objects.equals(this.resultCode, tokenSuccess.resultCode) &&
        Objects.equals(this.token, tokenSuccess.token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resultCode, token);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenSuccess {\n");
    
    sb.append("    resultCode: ").append(toIndentedString(resultCode)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
