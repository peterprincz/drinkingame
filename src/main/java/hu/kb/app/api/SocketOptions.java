package hu.kb.app.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public @Data @NoArgsConstructor
class SocketOptions {

    List<String> options = new ArrayList<>();

}
