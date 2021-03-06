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
import io.pestaKit.tasks.api.dto.Stage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Task
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-01-25T00:04:53.799+01:00")
public class Task   {
  @SerializedName("number")
  private BigDecimal number = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("stages")
  private List<Stage> stages = new ArrayList<Stage>();

  public Task number(BigDecimal number) {
    this.number = number;
    return this;
  }

   /**
   * number of the task
   * @return number
  **/
  @ApiModelProperty(example = "null", value = "number of the task")
  public BigDecimal getNumber() {
    return number;
  }

  public void setNumber(BigDecimal number) {
    this.number = number;
  }

  public Task name(String name) {
    this.name = name;
    return this;
  }

   /**
   * name of the task
   * @return name
  **/
  @ApiModelProperty(example = "null", value = "name of the task")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Task stages(List<Stage> stages) {
    this.stages = stages;
    return this;
  }

  public Task addStagesItem(Stage stagesItem) {
    this.stages.add(stagesItem);
    return this;
  }

   /**
   * array of the stages of the task
   * @return stages
  **/
  @ApiModelProperty(example = "null", value = "array of the stages of the task")
  public List<Stage> getStages() {
    return stages;
  }

  public void setStages(List<Stage> stages) {
    this.stages = stages;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Task task = (Task) o;
    return Objects.equals(this.number, task.number) &&
        Objects.equals(this.name, task.name) &&
        Objects.equals(this.stages, task.stages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, name, stages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Task {\n");
    
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    stages: ").append(toIndentedString(stages)).append("\n");
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

