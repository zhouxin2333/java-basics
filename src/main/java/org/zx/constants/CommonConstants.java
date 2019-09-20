package org.zx.constants;

import java.util.function.BinaryOperator;

/**
 * @author zhouxin
 * @since 2019/9/20
 */
public interface CommonConstants {
    BinaryOperator getLeftBinaryOperator = (left, right) -> left;
    BinaryOperator getRightBinaryOperator = (left, right) -> right;
}
