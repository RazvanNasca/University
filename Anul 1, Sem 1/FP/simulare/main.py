from teste.teste import Teste
from repos.repos import *
from validari.validari import *
from ui.ui import *

teste = Teste()
teste.run_teste()

RepoStud = RepoStud("stud.txt")
RepoLab = RepoLab("lab.txt")

ValidStud = ValidStud()
ValidLab = ValidLab()

ui = LabUI(RepoStud, RepoLab)
ui.run()