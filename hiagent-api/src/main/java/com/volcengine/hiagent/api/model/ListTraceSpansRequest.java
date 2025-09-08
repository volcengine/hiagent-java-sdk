// Copyright (c) 2024 Bytedance Ltd. and/or its affiliates
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.volcengine.hiagent.api.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

// {"SimpleFilters":[{"SpanType":"RootSpan","TimeRange":{"Operator":"In","Value":{"Start":"2025-09-05T15:49:41.546Z","End":"2025-09-08T15:49:41.546Z"}}}],"ComplexFilters":[{"Relation":"And","Filters":[{"Input":{"Operator":"Has","Value":"xx"}},{"Output":{"Operator":"Has","Value":"xx"}},{"OutputToken":{"Operator":"In","Value":{"Min":1,"Max":100}}},{"Latency":{"Operator":"In","Value":{"Min":1,"Max":100}}}]}],"PageSize":100,"Sort":[{"SortBy":"StartTime","SortOrder":"Desc"}],"WorkspaceID":"d2ogc6f27i3c72tqs61g"}
public class ListTraceSpansRequest {
  @SerializedName("WorkspaceID")
  private String workspaceID;
  @SerializedName("PageSize")
  private Integer pageSize;
  @SerializedName("Sort")
  private List<Order> sort;
  @SerializedName("SimpleFilters")
  private List<SimpleFilter> simpleFilters;
  @SerializedName("ComplexFilters")
  private List<ComplexFilter> complexFilters;

  public String getWorkspaceID() {
    return workspaceID;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public List<Order> getSort() {
    return sort;
  }

  public List<SimpleFilter> getSimpleFilters() {
    return simpleFilters;
  }

  public List<ComplexFilter> getComplexFilters() {
    return complexFilters;
  }

  public void setWorkspaceID(String workspaceID) {
    this.workspaceID = workspaceID;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public void setSort(List<Order> sort) {
    this.sort = sort;
  }

  public void setSimpleFilters(List<SimpleFilter> simpleFilters) {
    this.simpleFilters = simpleFilters;
  }

  public void setComplexFilters(List<ComplexFilter> complexFilters) {
    this.complexFilters = complexFilters;
  }

  public static class Order {
    private String sortBy;
    private String sortOrder;

    public String getSortBy() {
      return sortBy;
    }

    public String getSortOrder() {
      return sortOrder;
    }

    public void setSortBy(String sortBy) {
      this.sortBy = sortBy;
    }

    public void setSortOrder(String sortOrder) {
      this.sortOrder = sortOrder;
    }
  }

  public enum SpanType {
    @SerializedName("RootSpan")
    RootSpan,
    @SerializedName("LLMCall")
    LLMCall,
    @SerializedName("AllSpan")
    AllSpan,
  }

  public enum Operator {
    @SerializedName("In")
    In,
    @SerializedName("NotIn")
    NotIn,
    @SerializedName("Has")
    Has,
    @SerializedName("NotHas")
    NotHas,
    @SerializedName("Equal")
    Equal,
    @SerializedName("NotEqual")
    NotEqual,
    @SerializedName("Gt")
    Gt,
    @SerializedName("Gte")
    Gte,
    @SerializedName("Lt")
    Lt,
    @SerializedName("Lte")
    Lte,
    @SerializedName("RawIn")
    RawIn,
    @SerializedName("RawNotIn")
    RawNotIn,
    @SerializedName("IsNull")
    IsNull,
    @SerializedName("NotNull")
    NotNull,
  }

  public static class SimpleFilter {
    @SerializedName("SpanType")
    private SpanType spanType;
    @SerializedName("TimeRange")
    private TimeRange timeRange;

    public SpanType getSpanType() {
      return spanType;
    }

    public TimeRange getTimeRange() {
      return timeRange;
    }

    public void setSpanType(SpanType spanType) {
      this.spanType = spanType;
    }

    public void setTimeRange(TimeRange timeRange) {
      this.timeRange = timeRange;
    }
  }

  public static class TimeRange {
    private String operator;
    private Value value;

    public String getOperator() {
      return operator;
    }

    public Value getValue() {
      return value;
    }

    public void setOperator(String operator) {
      this.operator = operator;
    }

    public void setValue(Value value) {
      this.value = value;
    }

    public static class Value {
      private String start;
      private String end;

      public String getStart() {
        return start;
      }

      public String getEnd() {
        return end;
      }

      public void setStart(String start) {
        this.start = start;
      }

      public void setEnd(String end) {
        this.end = end;
      }
    }
  }

  public static class ComplexFilter {
    @SerializedName("Relation")
    private String relation;
    @SerializedName("Filters")
    private List<Filter> filters;

    public String getRelation() {
      return relation;
    }

    public void setRelation(String relation) {
      this.relation = relation;
    }

    public List<Filter> getFilters() {
      return filters;
    }

    public void setFilters(List<Filter> filters) {
      this.filters = filters;
    }

    public static class Filter {
      @SerializedName("SpanType")
      private String spanType;
      @SerializedName("TimeRange")
      private TimeRange timeRange;
      @SerializedName("Input")
      private FilterWithStr input;
      @SerializedName("Output")
      private FilterWithStr output;
      @SerializedName("OutputToken")
      private FilterWithNumRange outputToken;
      @SerializedName("Latency")
      private FilterWithNumRange latency;

      public String getSpanType() {
        return spanType;
      }

      public void setSpanType(String spanType) {
        this.spanType = spanType;
      }

      public TimeRange getTimeRange() {
        return timeRange;
      }

      public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
      }

      public FilterWithStr getInput() {
        return input;
      }

      public void setInput(FilterWithStr input) {
        this.input = input;
      }

      public FilterWithStr getOutput() {
        return output;
      }

      public void setOutput(FilterWithStr output) {
        this.output = output;
      }

      public FilterWithNumRange getOutputToken() {
        return outputToken;
      }

      public void setOutputToken(FilterWithNumRange outputToken) {
        this.outputToken = outputToken;
      }

      public FilterWithNumRange getLatency() {
        return latency;
      }

      public void setLatency(FilterWithNumRange latency) {
        this.latency = latency;
      }

    }
  }

  public static class FilterWithStr {
    @SerializedName("Operator")
    private String operator;
    @SerializedName("Value")
    private String value;

    public String getOperator() {
      return operator;
    }

    public void setOperator(String operator) {
      this.operator = operator;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  public static class FilterWithInt {
    @SerializedName("Operator")
    private String operator;
    @SerializedName("Value")
    private Integer value;

    public String getOperator() {
      return operator;
    }

    public void setOperator(String operator) {
      this.operator = operator;
    }

    public Integer getValue() {
      return value;
    }

    public void setValue(Integer value) {
      this.value = value;
    }
  }

  public static class FilterWithNumRange {
    @SerializedName("Operator")
    private String operator;
    @SerializedName("Value")
    private NumRange value;

    public String getOperator() {
      return operator;
    }

    public void setOperator(String operator) {
      this.operator = operator;
    }

    public NumRange getValue() {
      return value;
    }

    public void setValue(NumRange value) {
      this.value = value;
    }

    public static class NumRange {
      @SerializedName("Min")
      private Integer min;
      @SerializedName("Max")
      private Integer max;

      public Integer getMin() {
        return min;
      }

      public void setMin(Integer min) {
        this.min = min;
      }

      public Integer getMax() {
        return max;
      }

      public void setMax(Integer max) {
        this.max = max;
      }
    }
  }

  public static class FilterWithStrArr {
    @SerializedName("Operator")
    private String operator;
    @SerializedName("Value")
    private List<String> value;

    public String getOperator() {
      return operator;
    }

    public void setOperator(String operator) {
      this.operator = operator;
    }

    public List<String> getValue() {
      return value;
    }

    public void setValue(List<String> value) {
      this.value = value;
    }
  }

}
