package com.tenniscourts.exceptions;

import java.util.function.Supplier;

/**
 * The type Entity not found exception.
 */
public class EntityNotFoundException extends RuntimeException 
									 implements Supplier<EntityNotFoundException>{
  /**
   * Instantiates a new Entity not found exception.
   *
   * @param msg the msg
   */
  public EntityNotFoundException(String msg){
        super(msg);
    }

    private EntityNotFoundException(){}

	@Override
	public EntityNotFoundException get() {
		return this;
	}
}
