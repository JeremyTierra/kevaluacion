import { create } from 'zustand'
import { persist } from 'zustand/middleware'
import { RegisterInput, User, LoginInput } from './types'
import { AuthService } from '@/shared/services/auth'

interface AuthState {
  user: User | null
  tokens: string | null
  isAuthenticated: boolean
  isLoading: boolean
  error: string | null
  register: (data: RegisterInput) => Promise<void>
  login: (credentials: LoginInput) => Promise<void>
  logout: () => void
  setUser: (user: User | null) => void
  setTokens: (tokens: string | null) => void
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      tokens: null,
      isAuthenticated: false,
      isLoading: false,
      error: null,

      setUser: (user) => {
        set({ user, isAuthenticated: !!user })
      },

      setTokens: (tokens) => {
        set({ tokens })
      },

      register: async (data) => {
        set({ isLoading: true, error: null })
        try {
          const response = await AuthService.register(data)
          set({ 
            user: response.user,
            tokens: response.token,
            isAuthenticated: true,
            isLoading: false 
          })
        } catch (error) {
          set({ 
            error: (error as Error).message,
            isLoading: false 
          })
        }
      },

      login: async (credentials) => {
        set({ isLoading: true, error: null })
        try {
          const response = await AuthService.login(credentials)
          set({ 
            user: response.user,
            tokens: response.token,
            isAuthenticated: true,
            isLoading: false 
          })
        } catch (error) {
          set({ 
            error: (error as Error).message,
            isLoading: false 
          })
        }
      },

      logout: () => {
        console.log('🚪 Logging out and clearing localStorage...');
        
        // Limpiar el estado de Zustand
        set({ 
          user: null,
          tokens: null,
          isAuthenticated: false,
          isLoading: false,
          error: null
        })
        
        // Limpiar manualmente el localStorage por si acaso
        try {
          localStorage.removeItem('auth-storage');
          console.log('🗑️ localStorage cleared manually');
        } catch (error) {
          console.warn('⚠️ Could not clear localStorage:', error);
        }
        
        console.log('✅ Logout completed');
      }
    }),
    {
      name: 'auth-storage', // nombre para el almacenamiento
      partialize: (state) => ({ 
        user: state.user,
        tokens: state.tokens,
        isAuthenticated: state.isAuthenticated
      }) // solo guardamos estos campos
    }
  )
)
