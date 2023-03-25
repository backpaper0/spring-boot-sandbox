# spring-security-multiple-logins-example

1つのアプリケーションへ複数のログイン（というか認証まわり一式）を同居させる例。

`/protected1/**`と`/protected2/**`に異なる`SecurityFilterChain`と`UserDetailsService`を適用している。

