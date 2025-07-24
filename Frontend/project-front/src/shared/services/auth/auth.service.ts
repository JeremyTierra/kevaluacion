import { httpClient } from '../http-client';
import { LoginInput, RegisterInput, User } from '@/shared/store/auth/types';

export interface AuthResponse {
  user: User;
  token: string;
}

export class AuthService {
  private static readonly BASE_PATH = '/auth';

  static async login(credentials: LoginInput): Promise<AuthResponse> {
    const { data } = await httpClient.post<AuthResponse>(
      `${this.BASE_PATH}/login`,
      credentials
    );
    return data;
  }

  static async register(userData: RegisterInput): Promise<AuthResponse> {
    const { data } = await httpClient.post<AuthResponse>(
      `${this.BASE_PATH}/register`,
      userData
    );
    return data;
  }

  // Removido el endpoint /me ya que no es necesario
}
