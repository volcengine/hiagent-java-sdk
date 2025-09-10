package com.volcengine.hiagent.eva;

import com.volcengine.hiagent.api.model.eva.base.Cell;
import com.volcengine.hiagent.api.model.eva.base.EvaTaskResultTargetContentPair;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface InferenceFunction {
    List<EvaTaskResultTargetContentPair> execute(List<Map<String, Cell>> Case) throws Error;
}