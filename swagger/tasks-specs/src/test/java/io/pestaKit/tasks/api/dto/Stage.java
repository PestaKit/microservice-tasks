/**
 * AMT-TASKS API
 * api for porject AMT PestaKit microservice tasks
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.pestaKit.tasks.api.dto;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;


/**
 * Stage
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-01-25T00:04:53.799+01:00")
public class Stage   {
  @SerializedName("position")
  private BigDecimal position = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("value")
  private String value = null;

  public Stage position(BigDecimal position) {
    this.position = position;
    return this;
  }

   /**
   * position of the stage in the task
   * @return position
  **/
  @ApiModelProperty(example = "null", value = "position of the stage in the task")
  public BigDecimal getPosition() {
    return position;
  }

  public void setPosition(BigDecimal position) {
    this.position = position;
  }

  public Stage name(String name) {
    this.name = name;
    return this;
  }

   /**
   * name of stage
   * @return name
  **/
  @ApiModelProperty(example = "null", value = "name of stage")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Stage value(String value) {
    this.value = value;
    return this;
  }

   /**
   * value of the stage
   * @return value
  **/
  @ApiModelProperty(example = "null", value = "value of the stage")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Stage stage = (Stage) o;
    return Objects.equals(this.position, stage.position) &&
        Objects.equals(this.name, stage.name) &&
        Objects.equals(this.value, stage.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, name, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Stage {\n");
    
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

