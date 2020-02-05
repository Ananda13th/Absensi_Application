package example.com.domain.usecase;

import io.reactivex.Single;

public interface SingleUseCaseWithParam<INPUT,OUTPUT> {
    Single<OUTPUT> execute(INPUT parameter);
}
