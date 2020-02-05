package example.com.domain.usecase;

import io.reactivex.Single;

public interface SingleUseCase<OUTPUT> {
    Single<OUTPUT> execute();
}
