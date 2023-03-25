# EnvironmentPostProcessorの例

例としてHerokuのように環境変数`PORT`でバインドするポートが渡される場合に、そのポートを`server.port`プロパティへ設定する`EnvironmentPostProcessor`実装を作ってみた。

