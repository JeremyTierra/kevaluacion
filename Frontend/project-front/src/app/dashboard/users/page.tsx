'use client';

import { useState, useEffect, useCallback } from 'react';
import { User } from '@/shared/services/users/types';
import { UserService } from '@/shared/services/users';
import { DataTable } from '@/components/molecules/data-table/data-table';
import { ColumnDef } from '@tanstack/react-table';
import { useAuth } from '@/shared/hooks/useAuth';
import { useRole } from '@/shared/hooks/useRole';
import { UnauthorizedPage } from '@/components/template/UnauthorizedPage';

export default function UsersPage() {
  const { user } = useAuth();
  const { isAdmin } = useRole();
  const [users, setUsers] = useState<User[]>([]);

  // Si no es admin, mostrar página de no autorizado
  if (user && !isAdmin()) {
    return <UnauthorizedPage />;
  }

  const columns: ColumnDef<User>[] = [
    {
      accessorKey: 'username',
      header: 'Nombre',
    },
    {
      accessorKey: 'email',
      header: 'Correo',
    },
    {
      accessorKey: 'role',
      header: 'Rol',
    },
  ];

  const fetchUsers = useCallback(async () => {
    try {
      console.log('🔍 Fetching users...');
      const response = await UserService.getUsers();
      console.log('🔍 Users response:', response);
      setUsers(response.data);
      console.log('🔍 Users set to state:', response.data);
    } catch (error) {
      console.error('❌ Error fetching users:', error);
    }
  }, []);

  useEffect(() => {
    fetchUsers();
  }, [fetchUsers]);

  return (
    <div className="min-h-screen">
      <main className="container mx-auto px-4 py-8">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-xl font-semibold">Usuarios</h2>
        </div>

        <DataTable
          columns={columns}
          data={users}
        />
      </main>
    </div>
  );
} 
