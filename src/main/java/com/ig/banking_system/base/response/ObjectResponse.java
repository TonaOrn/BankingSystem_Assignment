package com.ig.banking_system.base.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectResponse<T> {
    T results;
    ResponseMessage response = new ResponseMessage().success();

    public ObjectResponse(T results) {
        this.results = results;
        this.response = new ResponseMessage().success();
    }
}
