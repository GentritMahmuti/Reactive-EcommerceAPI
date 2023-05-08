package io.kodelabs.base.exceptions;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.BlockingIterable;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.smallrye.mutiny.subscription.Cancellable;
import io.smallrye.mutiny.unchecked.Unchecked;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

public class CustomExceptionMapper {
  public static void main(String[] args) {
      Multi.createFrom().items(1,2,3,4,5)
              .select().first(integer -> integer>3)
              .subscribe().with(System.out::println);
  }
}
