package br.com.orbitall.channels.canonicals;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponse(

         UUID id,
         String fullName,
         String email,
         String phone,
         LocalDateTime createdAt,
         LocalDateTime updatedAt,
         boolean active

) {
}
