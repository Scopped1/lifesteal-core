package it.scopped.lifestealcore.tpa;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaRequestServiceImpl implements TpaRequestService {

    private final Map<UUID, TpaRequest> currentRequests = new HashMap<>();

}
