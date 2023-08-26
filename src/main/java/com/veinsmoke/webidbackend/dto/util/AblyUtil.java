package com.veinsmoke.webidbackend.dto.util;

import io.ably.lib.rest.AblyRest;
import io.ably.lib.types.AblyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AblyUtil {

    private AblyRest ablyRest;

    @Value( "${ably.apikey}" )
    private void setAblyRest(String apiKey) throws AblyException {
        ablyRest = new AblyRest(apiKey);
    }

    public boolean publishToChannel(String channelName, String name, Object data) {
        try {
            ablyRest.channels.get(channelName).publish(name, data);
        } catch (AblyException err) {
            System.out.println(err.errorInfo);
            return false;
        }
        return true;
    }

}
