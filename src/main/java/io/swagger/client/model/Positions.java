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
 * Positions
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-04-09T08:07:59.802Z[GMT]")
public class Positions {
  @SerializedName("HoldID")
  private String holdID = null;

  @SerializedName("Qty")
  private Integer qty = null;

  public Positions holdID(String holdID) {
    this.holdID = holdID;
    return this;
  }

   /**
   * 返済建玉ID
   * @return holdID
  **/
  @Schema(example = "20200702E01N04699739", description = "返済建玉ID")
  public String getHoldID() {
    return holdID;
  }

  public void setHoldID(String holdID) {
    this.holdID = holdID;
  }

  public Positions qty(Integer qty) {
    this.qty = qty;
    return this;
  }

   /**
   * 返済建玉数量
   * @return qty
  **/
  @Schema(example = "500", description = "返済建玉数量")
  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Positions positions = (Positions) o;
    return Objects.equals(this.holdID, positions.holdID) &&
        Objects.equals(this.qty, positions.qty);
  }

  @Override
  public int hashCode() {
    return Objects.hash(holdID, qty);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Positions {\n");
    
    sb.append("    holdID: ").append(toIndentedString(holdID)).append("\n");
    sb.append("    qty: ").append(toIndentedString(qty)).append("\n");
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
