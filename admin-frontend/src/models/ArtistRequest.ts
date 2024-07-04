export default interface ArtistRequest {
  name: string;
  alias: string;
  isBand: boolean;
  isOfficial: boolean;
  isIndie: boolean;
  description: string;
  typeIds: string[];
  avatarId: string;
  birthday: string;
  coverId: string;
  countryId: string;
}
