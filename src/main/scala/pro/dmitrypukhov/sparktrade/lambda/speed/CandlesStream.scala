package pro.dmitrypukhov.sparktrade.lambda.speed

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.slf4j.{Logger, LoggerFactory}
import pro.dmitrypukhov.sparktrade.datamarts.prices.Candle
import pro.dmitrypukhov.sparktrade.lambda.FinamEntityConverter

/**
 * Speed layer. Processing candles.
 */
class CandlesStream {
  protected val log: Logger = LoggerFactory.getLogger(this.getClass)
  protected val config: Config = ConfigFactory.load()
  protected val spark: SparkSession = SparkSession.active

  import spark.implicits._

  /**
   * Create stream, transform entities from raw to Candles
   */
  def process(df: DataFrame): Dataset[Candle] = {
    log.info("Speed layer. Start Candles stream.")
    val converter = new FinamEntityConverter
    df.map(converter.asCandle)
  }
}
