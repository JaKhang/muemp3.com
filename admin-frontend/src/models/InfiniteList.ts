export default interface InfiniteList<T> {
  total: number;
  content: T[];
  hasMore: number;
}
