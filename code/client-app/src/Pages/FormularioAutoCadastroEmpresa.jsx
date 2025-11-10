import EmpresaService from '../Services/EmpresaService';

const handleSubmit = async (event) => {
  event.preventDefault();
  const empresa = {
    email: formValues.email,
    senha: formValues.senha,
    nomeFantasia: formValues.nomeFantasia,
    razaoSocial: formValues.razaoSocial,
    cnpj: formValues.cnpj,
  };
  try {
    await EmpresaService.create(empresa);
    alert('Empresa cadastrada com sucesso!');
  } catch (error) {
    console.error('Erro ao cadastrar empresa:', error);
  }
};

<form onSubmit={handleSubmit}> {/* Existing form code */}</form>