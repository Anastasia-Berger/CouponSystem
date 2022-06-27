package com.jb.CouponSystem.security;

import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenManager {
    private Map<UUID, Information> tokens = new HashMap<>();

    public UUID addToken(Information information) {
        //delete previous tokens of current user id;
        deleteEntriesByUserId(information.getId());

        // generate new token
        UUID uuid = UUID.randomUUID();

        // put the token within the Information Object
        tokens.put(uuid, information);

        return uuid;
    }

    public void deleteEntriesByUserId(int id) {
        tokens.entrySet().removeIf(obj -> obj.getValue().getId() == id);
    }

    public int getUserID(UUID uuid) throws CouponSystemException {

        Information information = tokens.get(uuid);
        if (information == null) {
            throw new CouponSystemException(ErrMsg.ID_NOT_EXIST);
        }
        return tokens.get(uuid).getId();
    }

    public ClientType getClientType(UUID uuid) throws CouponSystemException {

        Information information = tokens.get(uuid);
        if (information == null) {
            throw new CouponSystemException(ErrMsg.ID_NOT_EXIST);
        }
        return tokens.get(uuid).getClientType();
    }

    public boolean isAdmin(UUID uuid) throws CouponSystemException {
        Information information = tokens.get(uuid);
        if (information == null) {
            throw new CouponSystemException(ErrMsg.ID_NOT_EXIST);
        }
        if (!information.getClientType().equals(ClientType.ADMINISTRATOR)) {
            throw new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT);
        }

        return true;
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void removeExpiredOver30Min() {
        tokens.entrySet().removeIf(entry -> entry.getValue().getTime().isAfter(LocalDateTime.now().minusMinutes(30)));
    }

}
