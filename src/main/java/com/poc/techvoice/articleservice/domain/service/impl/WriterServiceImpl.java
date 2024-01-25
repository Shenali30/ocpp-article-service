package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.application.exception.type.ServerException;
import com.poc.techvoice.articleservice.application.transport.request.entities.UpdateProfileRequest;
import com.poc.techvoice.articleservice.domain.entities.User;
import com.poc.techvoice.articleservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.articleservice.domain.enums.Role;
import com.poc.techvoice.articleservice.domain.exception.DomainException;
import com.poc.techvoice.articleservice.domain.service.WriterService;
import com.poc.techvoice.articleservice.domain.util.UtilityService;
import com.poc.techvoice.articleservice.external.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class WriterServiceImpl extends UtilityService implements WriterService {

    private final UserRepository userRepository;

    @Override
    public BaseResponse updateProfile(UpdateProfileRequest request) throws ServerException, DomainException {

        try {
            log.debug(LoggingConstants.EDIT_PROFILE_LOG, "Update user profile", LoggingConstants.STARTED);

            User user = userRepository.findByEmail(request.getEmail());
            if (Objects.nonNull(user)) {

                user.setRole(Role.WRITER);
                user.setName(request.getName());
                user.setProfileDescription(request.getProfileDescription());
                user.setCountryOfOrigin(request.getCountryOfOrigin());
                userRepository.save(user);

                log.debug(LoggingConstants.EDIT_PROFILE_LOG, "Update user profile", LoggingConstants.ENDED);
                return getSuccessBaseResponse("User profile updated successfully");

            } else {
                log.error(LoggingConstants.EDIT_PROFILE_ERROR, ResponseEnum.INVALID_USER.getDesc(), ResponseEnum.INVALID_USER.getDesc(), null);
                throw new DomainException(ResponseEnum.INVALID_USER.getDesc(), ResponseEnum.INVALID_USER.getCode(), ResponseEnum.INVALID_USER.getDisplayDesc());
            }

        } catch (DomainException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(LoggingConstants.EDIT_PROFILE_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }
}
