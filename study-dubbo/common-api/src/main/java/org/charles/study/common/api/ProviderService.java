package org.charles.study.common.api;

import org.charles.study.common.params.PayParams;

public interface ProviderService {

    String printMessage(String message);

    String pay(PayParams params);
}
