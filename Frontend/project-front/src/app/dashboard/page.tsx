'use client'

import { useCallback, useEffect, useState } from 'react'
import { Button } from '@/components/atoms'
import { ProjectForm } from './(main)/components/ProjectForm'
import { DataTable } from '@/components/molecules/data-table/data-table'
import { Project } from '@/shared/services/projects/types'
import { ProjectService } from '@/shared/services/projects/projects.service'
import { Sheet, SheetContent, SheetHeader, SheetTitle, SheetTrigger } from '@/components/atoms/sheet'
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from '@/components/atoms/dialog'
import { ColumnDef } from '@tanstack/react-table'
import { format } from 'date-fns'
import { es } from 'date-fns/locale'
import { Edit, Plus, Trash } from 'lucide-react'

export default function DashboardPage() {
  const [projects, setProjects] = useState<Project[]>([])
  const [selectedProject, setSelectedProject] = useState<Project | null>(null)
  const [isSubmitting, setIsSubmitting] = useState(false)

  const fetchProjects = useCallback(async () => {
    try {
      const response = await ProjectService.getProjects()
      console.log('Projects fetched:', response);
      setProjects(response.data)
    } catch (error) {
      console.error('Error fetching projects:', error)
    } finally {
    }
  }, [])

  useEffect(() => {
    fetchProjects();
    
  }, [])

  const handleCreateProject = async (data: { name: string; description: string }) => {
    console.log(data);
    
    setIsSubmitting(true)
    try {
      await ProjectService.createProject(data)
      fetchProjects()
    } catch (error) {
      console.error('Error creating project:', error)
    } finally {
      setIsSubmitting(false)
    }
  }

  const handleUpdateProject = async (data: { name: string; description: string }) => {
    if (!selectedProject) return
    setIsSubmitting(true)
    try {
      await ProjectService.updateProject(selectedProject.id, data)
      fetchProjects()
      setSelectedProject(null)
    } catch (error) {
      console.error('Error updating project:', error)
    } finally {
      setIsSubmitting(false)
    }
  }

  const handleDeleteProject = async (id: string) => {
    try {
      await ProjectService.deleteProject(Number(id))
      fetchProjects()
    } catch (error) {
      console.error('Error deleting project:', error)
    }
  }

  const columns: ColumnDef<Project>[] = [
    {
      accessorKey: 'name',
      header: 'Nombre',
    },
    {
      accessorKey: 'description',
      header: 'Descripción',
    },
    {
      accessorKey: 'createdAt',
      header: 'Fecha de creación',
      cell: ({ row }) => format(new Date(row.original.createdAt), 'PPP', { locale: es }),
    },
    {
      id: 'actions',
      cell: ({ row }) => {
        const project = row.original

        return (
          <div className="flex items-center gap-2">
            <Dialog>
              <DialogTrigger asChild>
                <Button
                  variant="ghost"
                  size="icon"
                  onClick={() => setSelectedProject(project)}
                >
                  <Edit className="h-4 w-4" />
                </Button>
              </DialogTrigger>
              <DialogContent>
                <DialogHeader>
                  <DialogTitle>Editar proyecto</DialogTitle>
                </DialogHeader>
                <ProjectForm
                  onSubmit={handleUpdateProject}
                  initialData={selectedProject ?? undefined}
                  isSubmitting={isSubmitting}
                />
              </DialogContent>
            </Dialog>
            <Button
              variant="ghost"
              size="icon"
              onClick={() => handleDeleteProject(String(project.id))}
            >
              <Trash className="h-4 w-4" />
            </Button>
          </div>
        )
      },
    },
  ]

  return (
    <div className="min-h-screen">
      <main className="container mx-auto px-4 py-8">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-xl font-semibold">Mis Proyectos</h2>
          <Sheet>
            <SheetTrigger asChild>
              <Button>
                <Plus className="mr-2 h-4 w-4" />
                Nuevo proyecto
              </Button>
            </SheetTrigger>
            <SheetContent>
              <SheetHeader>
                <SheetTitle>Crear nuevo proyecto</SheetTitle>
              </SheetHeader>
              <div className="m-4">
                <ProjectForm
                  onSubmit={handleCreateProject}
                  isSubmitting={isSubmitting}
                />
              </div>
            </SheetContent>
          </Sheet>
        </div>

        <DataTable
          columns={columns}
          data={projects}
        />
      </main>
    </div>
  )
}
